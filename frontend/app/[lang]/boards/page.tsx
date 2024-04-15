import React from 'react'
import { Locales } from '@/app/types/locales'
import { getBoards } from './utils/getBoards'
import { BannerDesktop, BannerMobile, Desktop, Mobile } from './components'
import { container } from '@/app/common'

interface Props {
    params: { lang: Locales }
}

export default async function page({ params: { lang } }: Props) {
    const boards = await getBoards()

    return (
        <div>
            <BannerDesktop />
            <BannerMobile />
            <div className={`${container.section} px-[16px]`}>
                <Desktop lang={lang} boards={boards} />
                <Mobile lang={lang} boards={boards} />
            </div>
        </div>
    )
}
