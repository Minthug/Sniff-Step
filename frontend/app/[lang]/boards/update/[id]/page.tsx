'use client'

import React, { useEffect, useState } from 'react'
import { container } from '@/app/common'
import { Board } from '@/app/types/board'
import { LocaleUpdateBoard, Locales } from '@/app/types/locales'
import { getLocales } from '@/app/utils/getLocales'
import { useFileChange, useUpdateBoard } from '@/app/hooks'
import { Desktop, Mobile } from './components'

interface Props {
    params: { lang: Locales; id: string }
}

export default function page({ params: { lang, id } }: Props) {
    const fileChangeState = useFileChange()
    const boardState = useUpdateBoard({ lang })
    const [text, setText] = useState<LocaleUpdateBoard>()

    const { getBoardById, adjustBoard, board } = boardState

    useEffect(() => {
        getLocales<LocaleUpdateBoard>('boards/update', lang).then(setText)
        getBoardById(id).then(adjustBoard)
    }, [])

    if (!text || !board)
        return (
            <div className="h-[calc(100vh-93px)] flex justify-center items-center">
                <div className="w-24 h-24 animate-spin rounded-full border-4 border-solid border-current border-e-transparent"></div>
            </div>
        )

    return (
        <section className={container.section}>
            <Desktop lang={lang} text={text} board={board} boardState={boardState} fileChangeState={fileChangeState} />
            <Mobile lang={lang} text={text} board={board} boardState={boardState} fileChangeState={fileChangeState} />
        </section>
    )
}
