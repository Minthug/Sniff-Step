import React from 'react'
import { LocaleFooter, LocaleHeader, LocaleHome, Locales } from '../types/locales'
import {
    DesktopFirst,
    DesktopFourth,
    DesktopSecond,
    DesktopThird,
    MobileFirst,
    MobileSecond,
    MobileThird,
    MobileFourth,
    Footer,
    Header
} from '../common'
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

            <DesktopFirst lang={lang} text={homeText} />
            <DesktopSecond text={homeText} />
            <DesktopThird text={homeText} />
            <DesktopFourth lang={lang} text={homeText} />

            <MobileFirst lang={lang} text={homeText} />
            <MobileSecond text={homeText} />
            <MobileThird text={homeText} />
            <MobileFourth lang={lang} text={homeText} />

            <Footer lang={lang} text={footerText} />
        </div>
    )
}
