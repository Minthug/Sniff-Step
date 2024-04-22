'use client'

import React from 'react'
import { Locales } from '../types/locales'
import { Header } from '../common'
import { Footer } from '../common/footer/Footer'
import { FirstSection, SecondSection, ThirdSection, FourthSection } from './components/desktop'
import { MobileFirstSection, MobileSecondSection, MobileThirdSection, MobileFourthSection } from './components/mobile'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    return (
        <div className="relative">
            <Header lang={lang} />
            <FirstSection lang={lang} />
            <SecondSection />
            <ThirdSection />
            <FourthSection lang={lang} />
            <MobileFirstSection lang={lang} />
            <MobileSecondSection />
            <MobileThirdSection />
            <MobileFourthSection lang={lang} />
            <Footer lang={lang} />
        </div>
    )
}
