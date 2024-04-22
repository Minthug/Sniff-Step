import React from 'react'
import { Home, Locales } from '../types/locales'
import { Footer, Header } from '../common'
import { FirstSection, SecondSection, ThirdSection, FourthSection } from './components/desktop'
import { MobileFirstSection, MobileSecondSection, MobileThirdSection, MobileFourthSection } from './components/mobile'
import { getLocales } from '../utils/getLocales'

interface Props {
    params: { lang: Locales }
}

export default async function page({ params: { lang } }: Props) {
    const data = await getLocales<Home>('home', lang)

    return (
        <div className="relative">
            <Header lang={lang} />

            <FirstSection lang={lang} text={data} />
            <SecondSection text={data} />
            <ThirdSection text={data} />
            <FourthSection lang={lang} text={data} />

            <MobileFirstSection lang={lang} text={data} />
            <MobileSecondSection text={data} />
            <MobileThirdSection text={data} />
            <MobileFourthSection lang={lang} text={data} />

            <Footer lang={lang} />
        </div>
    )
}
