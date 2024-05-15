'use client'

import React, { useEffect, useState } from 'react'
import { container } from '@/app/common'
import { LocalePostBoard, Locales } from '@/app/types/locales'
import { getLocales } from '@/app/utils/getLocales'
import { useFileChange, useBoards } from '@/app/hooks'
import { Desktop, Mobile } from './components'

interface Props {
    params: { lang: Locales; id: string }
}

export default function page({ params: { lang, id } }: Props) {
    const fileChangeState = useFileChange()
    const boardState = useBoards({ lang })
    const [text, setText] = useState<LocalePostBoard>()

    useEffect(() => {
        getLocales<LocalePostBoard>('boards/post', lang).then(setText)
    }, [])

    if (!text)
        return (
            <div className="h-[calc(100vh-93px)] flex justify-center items-center">
                <div className="w-24 h-24 animate-spin rounded-full border-4 border-solid border-current border-e-transparent"></div>
            </div>
        )

    return (
        <section className={container.section}>
            <Desktop lang={lang} text={text} boardId={id} boardState={boardState} fileChangeState={fileChangeState} />
            <Mobile lang={lang} text={text} boardId={id} boardState={boardState} fileChangeState={fileChangeState} />
        </section>
    )
}