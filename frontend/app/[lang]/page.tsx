import React from 'react'
import { Locales } from '../types/locales'
import { Header } from '../common'
import Button from '../common/components/Button'
import { Footer } from '../common/footer/Footer'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    return (
        <div className="relative">
            <Header lang={lang} />
            <div className={`xl:flex h-full min-h-screen mt-[76px]`}></div>
            <Footer lang={lang} />
        </div>
    )
}
