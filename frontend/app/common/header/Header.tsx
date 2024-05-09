'use client'

import React, { useEffect } from 'react'
import Desktop from './Desktop'
import Mobile from './Mobile'
import useHeader from '@/app/hooks/useHeader'
import { LocaleHeader } from '@/app/types/locales'

interface Props {
    lang: 'ko' | 'en'
    text: LocaleHeader
}

export function Header({ lang, text }: Props) {
    const headerStates = useHeader()

    useEffect(() => {
        if (localStorage.getItem('accessToken')) return

        fetch('/api/auth/google-profile', {
            credentials: 'include'
        }).then(async (res) => {
            const { data } = await res.json()
            if (data) {
                const accessToken = data.value
                localStorage.setItem('accessToken', accessToken)
            }
        })
    }, [])

    return (
        <>
            <Desktop lang={lang} text={text} headerStates={headerStates} />
            <Mobile lang={lang} text={text} headerStates={headerStates} />
        </>
    )
}
