'use client'

import React, { useEffect, useState } from 'react'
import { LocaleBoards, Locales } from '@/app/types/locales'
import { container } from '@/app/common'
import { BannerDesktop, BannerMobile, Desktop, Mobile } from '../components'
import { getLocales } from '@/app/utils/getLocales'
import { useRouter, useSearchParams } from 'next/navigation'
import { Board } from '@/app/types/board'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    const [loading, setLoading] = useState(true)
    const [text, setText] = useState<LocaleBoards>()
    const [boards, setBoards] = useState<Board[]>([])
    const router = useRouter()
    const params = useSearchParams()

    const handleSearch = async (keyword: string) => {
        const res = await fetch(`/api/boards/search?keyword=` + keyword, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        })

        const { data } = await res.json()
        setBoards(data)
        setLoading(false)
    }

    useEffect(() => {
        getLocales<LocaleBoards>('boards', lang).then(setText)
        const keyword = params.get('keyword')
        const reload = params.get('reload')
        if (reload) router.push(`/${lang}/boards/search?keyword=${keyword}`)
        if (keyword) handleSearch(keyword)
    }, [params])

    if (loading || !text)
        return (
            <div className="h-[calc(100vh-93px)] flex justify-center items-center">
                <div className="w-24 h-24 animate-spin rounded-full border-4 border-solid border-current border-e-transparent"></div>
            </div>
        )

    return (
        <div>
            <BannerDesktop text={text} />
            <BannerMobile text={text} />
            <div className={`${container.section} px-[16px]`}>
                <Desktop text={text} lang={lang} boards={boards} />
                <Mobile text={text} lang={lang} boards={boards} />
            </div>
        </div>
    )
}
