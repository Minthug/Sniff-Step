import React from 'react'
import { Locales } from '@/app/types/locales'
import { getBoards } from './utils/getBoards'
import { Desktop, Mobile } from './components'

interface Props {
    params: { lang: Locales }
}

export default async function page({ params: { lang } }: Props) {
    const boards = await getBoards()

    return (
        <>
            <Desktop boards={boards} />
            <Mobile boards={boards} />
        </>
    )
}
