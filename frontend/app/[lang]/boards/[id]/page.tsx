import React from 'react'
import { container } from '@/app/common'
import { Locales } from '@/app/types/locales'
import { getBoardById } from './utils/getBoardById'
import { D2CodingBold } from '@/app/fonts'
import Button from '@/app/common/components/Button'
import { changeDayToKorean, changeTimeToKorean } from '@/app/utils/changeDateUtils'
import { FaThumbsUp } from 'react-icons/fa6'
import { Desktop, Mobile } from './components'

interface Props {
    params: { lang: Locales; id: string }
}

export default async function page({ params: { lang, id } }: Props) {
    try {
        const board = await getBoardById(id)
        const dates = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']

        return (
            <div className={container.section}>
                <Desktop lang={lang} board={board} dates={dates} />
                <Mobile lang={lang} board={board} dates={dates} />
            </div>
        )
    } catch (error: any) {
        const { status, message } = JSON.parse(error.message)

        return (
            <div className={`${container.section} h-[480px] flex gap-4 flex-col justify-center items-center`}>
                <div className={`${D2CodingBold.className} flex gap-4 text-[48px] font-[700]`}>
                    <div>{message}</div>
                    <div>{status}</div>
                </div>
                <Button className="text-[48px]" contents="Go Back" href={`/${lang}/boards`} />
            </div>
        )
    }
}
