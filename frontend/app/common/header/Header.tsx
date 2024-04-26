'use client'

import React, { useState } from 'react'
import { LocaleHeader } from '../../types/locales'
import Desktop from './Desktop'
import Mobile from './Mobile'

interface Props {
    lang: 'ko' | 'en'
    text: LocaleHeader
}

export function Header({ lang, text }: Props) {
    const [onMobileMenu, setOnMobileMenu] = useState(false)
    const [onMobileSearch, setOnMobileSearch] = useState(false)

    return (
        <>
            <Desktop lang={lang} text={text} />
            <Mobile
                lang={lang}
                text={text}
                onMobileMenu={onMobileMenu}
                onMobileSearch={onMobileSearch}
                setOnMobileMenu={setOnMobileMenu}
                setOnMobileSearch={setOnMobileSearch}
            />
        </>
    )
}
