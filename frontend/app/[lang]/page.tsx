'use client'

import React, { useEffect, useState } from 'react'
import { Locales } from '../types/locales'
import { Header } from '../common'
import { Footer } from '../common/footer/Footer'
import { D2CodingBold } from '../fonts'
import { FirstSection } from './components/desktop/FirstSection'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    return (
        <div className="relative">
            <Header lang={lang} />
            <FirstSection />
            <Footer lang={lang} />
        </div>
    )
}
