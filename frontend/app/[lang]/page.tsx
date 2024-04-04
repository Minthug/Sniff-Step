import React from 'react'
import { Locales } from '../types/locales'
import Link from 'next/link'
import { D2CodingBold } from '../fonts'
import Header from '../common/Header'
import { container } from '../common/styles'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    return (
        <div className="relative">
            <Header />
            <div className={`${container}`}>
                <Link className={`${D2CodingBold.className}`} href="/[lang]/login" as={`/${lang}/login`}>
                    login
                </Link>
            </div>
        </div>
    )
}
