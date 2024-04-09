import React, { useState } from 'react'

export interface LoginStates {
    email: string
    password: string
    changeEmail: (e: React.ChangeEvent<HTMLInputElement>) => void
    changePassword: (e: React.ChangeEvent<HTMLInputElement>) => void
}

export function useLogin(): LoginStates {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const changeEmail = (e: React.ChangeEvent<HTMLInputElement>) => setEmail(e.target.value)

    const changePassword = (e: React.ChangeEvent<HTMLInputElement>) => setPassword(e.target.value)

    return {
        email,
        password,
        changeEmail,
        changePassword
    }
}
