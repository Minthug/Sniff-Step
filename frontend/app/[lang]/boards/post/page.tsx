'use client'

import React, { useEffect, useState } from 'react'
import { LocalePostBoard, Locales } from '@/app/types/locales'
import { useBoards, useFileChange } from '@/app/hooks/'
import { Desktop, Mobile } from './components'
import { getLocales } from '@/app/utils/getLocales'
import { container } from '@/app/common'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    const boardState = useBoards({ lang })
    const fileChangeState = useFileChange()
    const [text, setText] = useState<LocalePostBoard>()

    useEffect(() => {
        getLocales<LocalePostBoard>('boards/post', lang).then(setText)
    }, [])

    useEffect(() => {
        if (fileChangeState.file) {
            boardState.setImagesError(false)
        }
    }, [fileChangeState.file])

    if (!text)
        return (
            <div className="h-[calc(100vh-93px)] flex justify-center items-center">
                <div className="w-24 h-24 animate-spin rounded-full border-4 border-solid border-current border-e-transparent"></div>
            </div>
        )

    return (
        <section className={container.section}>
            <Desktop lang={lang} text={text} boardState={boardState} fileChangeState={fileChangeState} />
            <Mobile lang={lang} text={text} boardState={boardState} fileChangeState={fileChangeState} />
        </section>
    )
}
