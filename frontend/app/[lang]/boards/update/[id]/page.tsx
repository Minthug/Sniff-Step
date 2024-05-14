'use client'

import React, { useEffect, useState } from 'react'
import { LocaleRegisterWalker, Locales } from '@/app/types/locales'
import { getLocales } from '@/app/utils/getLocales'
import { useFileChange, useRegisterWalker } from '@/app/hooks'
import { Desktop, Mobile } from '@/app/[lang]/register-walker/components'

interface Props {
    params: { lang: Locales; id: string }
}

export default function page({ params: { lang, id } }: Props) {
    const fileChangeState = useFileChange()
    const registerWalkerState = useRegisterWalker({ lang })
    const [text, setText] = useState<LocaleRegisterWalker>()

    useEffect(() => {
        getLocales<LocaleRegisterWalker>('register-walker', lang).then(setText)
    }, [])

    if (!text)
        return (
            <div className="h-[calc(100vh-93px)] flex justify-center items-center">
                <div className="w-24 h-24 animate-spin rounded-full border-4 border-solid border-current border-e-transparent"></div>
            </div>
        )

    return (
        <>
            <Desktop lang={lang} text={text} registerWalkerState={registerWalkerState} fileChangeState={fileChangeState} />
            <Mobile lang={lang} text={text} registerWalkerState={registerWalkerState} fileChangeState={fileChangeState} />
        </>
    )
}
