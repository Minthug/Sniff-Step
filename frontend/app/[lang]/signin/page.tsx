'use client'

import React from 'react'
import { Locales } from '@/app/types/locales'
import { useLogin } from '@/app/hooks'
import { Desktop, Mobile } from './components'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    const loginStates = useLogin()

    return (
        <>
            <Desktop lang={lang} loginStates={loginStates} />
            <Mobile lang={lang} loginStates={loginStates} />
        </>
    )
}
