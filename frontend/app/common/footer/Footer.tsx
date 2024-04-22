import React from 'react'
import { Desktop } from './Desktop'
import { Mobile } from './Mobile'
import { LocaleFooter } from '@/app/types/locales'

interface Props {
    lang: string
    text: LocaleFooter
}

export function Footer({ lang, text }: Props) {
    return (
        <>
            <Desktop lang={lang} text={text} />
            <Mobile lang={lang} text={text} />
        </>
    )
}
