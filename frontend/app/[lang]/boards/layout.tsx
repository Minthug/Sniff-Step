import React from 'react'
import { Locales } from '@/app/types/locales'
import { Header, container } from '@/app/common'
import { BannerDesktop, BannerMobile } from './components'
import { Footer } from '@/app/common/footer/Footer'

interface Props {
    children: React.ReactNode
    params: { lang: Locales }
}

export default function page({ children, params: { lang } }: Props) {
    return (
        <div>
            <Header lang={lang} />
            {children}
            <Footer lang={lang} />
        </div>
    )
}
