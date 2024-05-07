import React from 'react'
import { LocaleBoards, Locales } from '@/app/types/locales'
import { getBoards } from './utils/getBoards'
import { BannerDesktop, BannerMobile, Desktop, Mobile } from './components'
import { container } from '@/app/common'
import { getLocales } from '@/app/utils/getLocales'

interface Props {
    params: { lang: Locales }
}

export default async function page({ params: { lang } }: Props) {
    const { data, total } = await getBoards()
    const boardsText = await getLocales<LocaleBoards>('boards', lang)

    return (
        <div>
            <BannerDesktop text={boardsText} />
            <BannerMobile text={boardsText} />
            <div className={`${container.section} px-[16px]`}>
                <Desktop text={boardsText} lang={lang} boards={data} />
                <Mobile lang={lang} boards={data} />
            </div>
        </div>
    )
}
