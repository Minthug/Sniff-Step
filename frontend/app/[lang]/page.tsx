import React from 'react'
import { Locales } from '../types/locales'
import Link from 'next/link'
import { D2CodingBold } from '../fonts'
import { Header, container } from '../common'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    return (
        <div className="relative">
            <Header lang={lang} />
            <div className={`${container}`}>
                <Link className={`${D2CodingBold.className}`} href={`/${lang}/signin`}>
                    login
                </Link>
            </div>
        </div>
    )
}
