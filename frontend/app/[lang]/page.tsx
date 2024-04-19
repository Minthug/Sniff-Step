'use client'

import React from 'react'
import { Locales } from '../types/locales'
import { Header } from '../common'
import { Footer } from '../common/footer/Footer'
import { FirstSection } from './components/desktop/FirstSection'
import { SecondSection } from './components/desktop/SecondSection'
import { ThirdSection } from './components/desktop/ThirdSection'
import FourthSection from './components/desktop/FourthSection'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    return (
        <div className="relative">
            <Header lang={lang} />
            <FirstSection />
            <SecondSection />
            <ThirdSection />
            <FourthSection />
            <Footer lang={lang} />
        </div>
    )
}
