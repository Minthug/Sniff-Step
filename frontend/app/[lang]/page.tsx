'use client'

import React from 'react'
import { Locales } from '../types/locales'
import { Header } from '../common'
import { Footer } from '../common/footer/Footer'
import { FirstSection } from './components/desktop/FirstSection'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    return (
        <div className="relative">
            <Header lang={lang} />
            <FirstSection />
            <div className="h-full px-[100px]">
                <img className="w-" src="/images/main/2-section.png" alt="" />
            </div>
            <Footer lang={lang} />
        </div>
    )
}
