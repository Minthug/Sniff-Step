import React from 'react'
import { container } from '@/app/common'
import { LocaleBoard, Locales } from '@/app/types/locales'
import { getBoardById } from './utils/getBoardById'
import { D2CodingBold } from '@/app/fonts'
import { Desktop, Mobile } from './components'
import Button from '@/app/common/components/Button'
import { getLocales } from '@/app/utils/getLocales'

interface Props {
    params: { lang: Locales; id: string }
}

export default async function page({ params: { lang, id } }: Props) {
    try {
        const board = await getBoardById(id)
        const dates = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        const text = await getLocales<LocaleBoard>('board', lang)

        return (
            <div className={container.section}>
                <Desktop lang={lang} text={text} board={board} dates={dates} />
                <Mobile lang={lang} text={text} board={board} dates={dates} />
            </div>
        )
    } catch (error: any) {
        const { status, message } = JSON.parse(error.message)

        return (
            <div
                className={`
                    xl:h-[calc(100vh-92px)]
                    ${container.section} h-screen flex gap-4 flex-col justify-center items-center
                `}
            >
                <div className={`${D2CodingBold.className} flex gap-4 text-[48px] font-[700]`}>
                    <div>{message}</div>
                    <div>{status}</div>
                </div>
                <Button className="text-[48px]" contents="Go Back" href={`/${lang}/boards`} />
            </div>
        )
    }
}
