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
    const text = await getLocales<LocaleBoards>('boards', lang)

    return (
        <div>
            <BannerDesktop text={text} />
            <BannerMobile text={text} />
            <div className={`${container.section} px-[16px]`}>
                <Desktop text={text} lang={lang} boards={data} />
                <Mobile text={text} lang={lang} boards={data} />
            </div>
        </div>
    )
}
