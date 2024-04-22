import React from 'react'
import { LocaleFooter, LocaleHeader, LocaleHome, Locales } from '../types/locales'
import { Footer, Header } from '../common'
import { FirstSection, SecondSection, ThirdSection, FourthSection } from './components/desktop'
import { MobileFirstSection, MobileSecondSection, MobileThirdSection, MobileFourthSection } from './components/mobile'
import { getLocales } from '../utils/getLocales'

interface Props {
    params: { lang: Locales }
}

export default async function page({ params: { lang } }: Props) {
    const homeText = await getLocales<LocaleHome>('home', lang)
    const headerText = await getLocales<LocaleHeader>('header', lang)
    const footerText = await getLocales<LocaleFooter>('footer', lang)

    return (
        <div className="relative">
            <Header lang={lang} text={headerText} />

            <FirstSection lang={lang} text={homeText} />
            <SecondSection text={homeText} />
            <ThirdSection text={homeText} />
            <FourthSection lang={lang} text={homeText} />

            <MobileFirstSection lang={lang} text={homeText} />
            <MobileSecondSection text={homeText} />
            <MobileThirdSection text={homeText} />
            <MobileFourthSection lang={lang} text={homeText} />

            <Footer lang={lang} text={footerText} />
        </div>
    )
}
