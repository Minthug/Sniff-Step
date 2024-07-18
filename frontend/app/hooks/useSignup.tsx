import { useState } from 'react'

export interface SignupStates {
    nickname: string
    email: string
    password: string
    phoneNumber: string
    isAgreed: boolean
    nicknameError: boolean
    emailError: boolean
    passwordError: boolean
    passwordLengthError: boolean
    phoneNumberError: boolean
    passwordLetterError: boolean
    isAgreedError: boolean
    changeNickname: (e: React.ChangeEvent<HTMLInputElement>) => void
    changeEmail: (e: React.ChangeEvent<HTMLInputElement>) => void
    changePassword: (e: React.ChangeEvent<HTMLInputElement>) => void
    changePhoneNumber: (e: React.ChangeEvent<HTMLInputElement>) => void
    changeIsAgreed: (e: React.ChangeEvent<HTMLInputElement>) => void
    handleLogin: () => Promise<string>
    handleRegister: () => Promise<boolean>
    handleGetProfile: (accessToken: string) => Promise<void>
}

export function useSignup(): SignupStates {
    const [nickname, setNickname] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')
    const [isAgreed, setIsAgreed] = useState(false)
    const [nicknameError, setNicknameError] = useState(false)
    const [emailError, setEmailError] = useState(false)
    const [passwordError, setPasswordError] = useState(false)
    const [passwordLengthError, setPasswordLengthError] = useState(false)
    const [passwordLetterError, setPasswordLetterError] = useState(false)
    const [phoneNumberError, setPhoneNumberError] = useState(false)
    const [isAgreedError, setIsAgreedError] = useState(false)

    const changeNickname = (e: React.ChangeEvent<HTMLInputElement>) => {
        setNickname(e.target.value)
        setNicknameError(false)
    }
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
    const changePhoneNumber = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPhoneNumber(e.target.value)
        setPhoneNumberError(false)
    }
    const changeIsAgreed = (e: React.ChangeEvent<HTMLInputElement>) => {
        setIsAgreed(e.target.checked)
        setIsAgreedError(false)
    }

    const handleGetProfile = async (accessToken: string) => {
        const res = await fetch('/api/auth/profile', {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        })

        if (!res.ok) {
            return
        }

        const data = await res.json()
        const { id } = data
        localStorage.setItem('userId', id)
    }

    const handleLogin = async () => {
        const res = await fetch('/api/signin/email-password', {
            method: 'POST',
            body: JSON.stringify({
                email,
                password
            })
        })

        const data = await res.json()
        const { accessToken, refreshToken } = data
        localStorage.setItem('accessToken', accessToken)
        localStorage.setItem('refreshToken', refreshToken)

        return accessToken
    }

    const handleRegister = async () => {
        if (!isAgreed) {
            setIsAgreedError(true)
            return false
        }

        const res = await fetch('/api/signup/email-password', {
            method: 'POST',
            body: JSON.stringify({
                nickname,
                email,
                password,
                phoneNumber
            })
        })

        if (!res.ok) {
            const { message } = await res.json()
            message.forEach((msg: string) => {
                switch (msg) {
                    case 'nickname should not be empty':
                        setNicknameError(true)
                        break
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
                    case 'phoneNumber should not be empty':
                        setPhoneNumberError(true)
                        break
                }
            })
            return false
        }

        return true
    }

    return {
        nickname,
        email,
        password,
        phoneNumber,
        isAgreed,
        nicknameError,
        emailError,
        passwordError,
        passwordLengthError,
        phoneNumberError,
        passwordLetterError,
        isAgreedError,
        changeNickname,
        changeEmail,
        changePassword,
        changePhoneNumber,
        changeIsAgreed,
        handleLogin,
        handleRegister,
        handleGetProfile
    }
}
