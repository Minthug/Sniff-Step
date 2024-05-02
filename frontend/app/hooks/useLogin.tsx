import React, { useState } from 'react'

export interface LoginStates {
    email: string
    password: string
    emailError: boolean
    passwordError: boolean
    passwordLengthError: boolean
    passwordLetterError: boolean
    changeEmail: (e: React.ChangeEvent<HTMLInputElement>) => void
    changePassword: (e: React.ChangeEvent<HTMLInputElement>) => void
    handleLogin: () => Promise<boolean>
}

export function useLogin(): LoginStates {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [emailError, setEmailError] = useState(false)
    const [passwordError, setPasswordError] = useState(false)
    const [passwordLengthError, setPasswordLengthError] = useState(false)
    const [passwordLetterError, setPasswordLetterError] = useState(false)

    const changeEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value)
        setEmailError(false)
    }

    const changePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value)
        setPasswordError(false)
        setPasswordLengthError(false)
        setPasswordLetterError(false)
    }

    const handleLogin = async () => {
        const res = await fetch('/api/signin/email-password', {
            method: 'POST',
            body: JSON.stringify({
                email,
                password
            })
        })

        if (!res.ok) {
            const { message } = await res.json()
            message.forEach((msg: string) => {
                switch (msg) {
                    case 'email must be an email':
                        setEmailError(true)
                        break
                    case 'email should not be empty':
                        setEmailError(true)
                        break
                    case 'password should not be empty':
                        setPasswordError(true)
                        break
                    case 'password must be longer than or equal to 6 characters':
                        setPasswordLengthError(true)
                        break
                    case 'password must contain letter and numbers':
                        setPasswordLetterError(true)
                        break
                }
            })
            return false
        }

        const { data } = await res.json()
        const { accessToken, refreshToken } = data
        localStorage.setItem('accessToken', accessToken)
        localStorage.setItem('refreshToken', refreshToken)
        return true
    }

    return {
        email,
        password,
        emailError,
        passwordError,
        passwordLengthError,
        passwordLetterError,
        changeEmail,
        changePassword,
        handleLogin
    }
}
