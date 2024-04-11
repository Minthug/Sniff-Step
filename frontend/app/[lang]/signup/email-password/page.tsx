'use client'

import React from 'react'
import { Locales } from '@/app/types/locales'
import { useSignup } from '@/app/hooks'
import { Desktop, Mobile } from './components'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    const signupStates = useSignup()

    return (
        <>
            <Desktop lang={lang} signupStates={signupStates} />
            <Mobile lang={lang} signupStates={signupStates} />
        </>
    )
}
