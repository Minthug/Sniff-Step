'use client'

import React from 'react'
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

    return (
        <>
            <Desktop lang={lang} text={text} headerStates={headerStates} />
            <Mobile lang={lang} text={text} headerStates={headerStates} />
        </>
    )
}
