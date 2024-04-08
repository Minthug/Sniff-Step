import React from 'react'
import { Locales } from '../types/locales'
import { Header } from '../common'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    return (
        <div className="relative">
            <Header lang={lang} />
        </div>
    )
}
