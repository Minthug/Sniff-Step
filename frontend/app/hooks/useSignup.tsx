import { useState } from 'react'

export interface SignupStates {
    name: string
    nickName: string
    email: string
    password: string
    phoneNumber: string
    isAgreed: boolean
    changeName: (e: React.ChangeEvent<HTMLInputElement>) => void
    changeNickname: (e: React.ChangeEvent<HTMLInputElement>) => void
    changeEmail: (e: React.ChangeEvent<HTMLInputElement>) => void
    changePassword: (e: React.ChangeEvent<HTMLInputElement>) => void
    changePhoneNumber: (e: React.ChangeEvent<HTMLInputElement>) => void
    changeIsAgreed: (e: React.ChangeEvent<HTMLInputElement>) => void
}

export function useSignup(): SignupStates {
    const [name, setName] = useState('')
    const [nickName, setNickName] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')
    const [isAgreed, setIsAgreed] = useState(false)

    const changeName = (e: React.ChangeEvent<HTMLInputElement>) => setName(e.target.value)
    const changeNickname = (e: React.ChangeEvent<HTMLInputElement>) => setNickName(e.target.value)
    const changeEmail = (e: React.ChangeEvent<HTMLInputElement>) => setEmail(e.target.value)
    const changePassword = (e: React.ChangeEvent<HTMLInputElement>) => setPassword(e.target.value)
    const changePhoneNumber = (e: React.ChangeEvent<HTMLInputElement>) => setPhoneNumber(e.target.value)
    const changeIsAgreed = (e: React.ChangeEvent<HTMLInputElement>) => setIsAgreed(e.target.checked)

    return {
        name,
        nickName,
        email,
        password,
        phoneNumber,
        isAgreed,
        changeName,
        changeNickname,
        changeEmail,
        changePassword,
        changePhoneNumber,
        changeIsAgreed
    }
}
