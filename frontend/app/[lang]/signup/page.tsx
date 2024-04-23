'use client'

import React, { useEffect, useState } from 'react'
import { LocaleSignup, Locales } from '@/app/types/locales'
import { Desktop, Mobile } from './components'
import { getLocales } from '@/app/utils/getLocales'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    const [text, setText] = useState<LocaleSignup>()

    useEffect(() => {
        getLocales<LocaleSignup>('signup', lang).then(setText)
    }, [])

    if (!text)
        return (
            <div className="h-[calc(100vh-93px)] flex justify-center items-center">
                <div className="w-24 h-24 animate-spin rounded-full border-4 border-solid border-current border-e-transparent" />
            </div>
        )

    return (
        <>
            <Desktop lang={lang} text={text} />
            <Mobile lang={lang} text={text} />
        </>
    )
}
