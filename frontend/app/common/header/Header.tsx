'use client'

import React, { useState } from 'react'
import { LocaleHeader } from '../../types/locales'
import Desktop from './Desktop'
import Mobile from './Mobile'
import useHeader from '@/app/hooks/useHeader'

interface Props {
    lang: 'ko' | 'en'
    text: LocaleHeader
}

export function Header({ lang, text }: Props) {
    const [onMobileMenu, setOnMobileMenu] = useState(false)
    const [onMobileSearch, setOnMobileSearch] = useState(false)
    const headerStates = useHeader()

    return (
        <>
            <Desktop lang={lang} text={text} headerStates={headerStates} />
            <Mobile lang={lang} text={text} headerStates={headerStates} />
        </>
    )
}
