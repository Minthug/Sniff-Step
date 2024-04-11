import React from 'react'
import Button from '../components/Button'
import { Desktop } from './Desktop'
import { Mobile } from './Mobile'

interface Props {
    lang: string
}

export function Footer({ lang }: Props) {
    return (
        <>
            <Desktop lang={lang} />
            <Mobile lang={lang} />
        </>
    )
}
