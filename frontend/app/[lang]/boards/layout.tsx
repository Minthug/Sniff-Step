import React from 'react'
import { LocaleFooter, LocaleHeader, Locales } from '@/app/types/locales'
import { Footer, Header } from '@/app/common'
import { getLocales } from '@/app/utils/getLocales'

interface Props {
    children: React.ReactNode
    params: { lang: Locales }
}

export default async function page({ children, params: { lang } }: Props) {
    const headerText = await getLocales<LocaleHeader>('header', lang)
    const footerText = await getLocales<LocaleFooter>('footer', lang)

    return (
        <div>
            <Header lang={lang} text={headerText} />
            {children}
            <Footer lang={lang} text={footerText} />
        </div>
    )
}
