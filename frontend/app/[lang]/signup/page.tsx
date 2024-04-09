'use client'

import React from 'react'
import { Locales } from '@/app/types/locales'
import { Desktop, Mobile } from './components'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    return (
        <>
            <Desktop lang={lang} />
            <Mobile lang={lang} />
        </>
    )
}
