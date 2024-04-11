'use client'
import React from 'react'
import { Locales } from '@/app/types/locales'
import { useRegisterWalker, useFileChange } from '@/app/hooks/'
import { Desktop, Mobile } from './components'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    const fileChangeState = useFileChange()
    const registerWalkerState = useRegisterWalker()

    return (
        <>
            <Desktop lang={lang} registerWalkerState={registerWalkerState} fileChangeState={fileChangeState} />
            <Mobile lang={lang} registerWalkerState={registerWalkerState} fileChangeState={fileChangeState} />
        </>
    )
}
