package com.github.hyeyoom.springoauth2example.config

import com.github.hyeyoom.springoauth2example.entity.User
import com.github.hyeyoom.springoauth2example.entity.UserRepository
import com.github.hyeyoom.springoauth2example.entity.dto.SessionUser
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpSession

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
    private val httpSession: HttpSession
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId
        val userNameAttributeName =
            userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
        val attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.attributes)
        val user: User = saveOrUpdate(attributes)
        val sessionUser = SessionUser(user)
        httpSession.setAttribute("user", sessionUser)
        return DefaultOAuth2User(
            Collections.singleton(SimpleGrantedAuthority(user.role.name)),
            attributes.attributes,
            attributes.nameAttributeKey
        )
    }

    private fun saveOrUpdate(attributes: OAuthAttributes): User {
        val maybeUser = userRepository.findByEmail(attributes.email)
        if (maybeUser != null) {
            maybeUser.name = attributes.name
            maybeUser.picture = attributes.picture
            return maybeUser
        }
        return userRepository.save(attributes.toEntity())
    }
}