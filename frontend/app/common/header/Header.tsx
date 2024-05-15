'use client'

import React, { useEffect } from 'react'
import Desktop from './Desktop'
import Mobile from './Mobile'
import useHeader from '@/app/hooks/useHeader'
import { LocaleHeader } from '@/app/types/locales'
import { useRouter, useSearchParams } from 'next/navigation'

interface Props {
    lang: 'ko' | 'en'
    text: LocaleHeader
}

export function Header({ lang, text }: Props) {
    const headerStates = useHeader()
    const searchParams = useSearchParams()
    const router = useRouter()

    useEffect(() => {
        if (searchParams.get('reload')) {
            router.replace(window.location.pathname)
            router.refresh()
        }
    }, [searchParams])

    return (
        <>
            <Desktop lang={lang} text={text} headerStates={headerStates} />
            <Mobile lang={lang} text={text} headerStates={headerStates} />
        </>
    )
}
