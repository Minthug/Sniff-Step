import React from 'react'
import Link from 'next/link'
import { Locale } from '@/i18n.config'
import { Board } from '@/app/types/board'
import { container } from '@/app/common'
import { LocaleBoards } from '@/app/types/locales'

interface Props {
    text: LocaleBoards
    lang: Locale
    boards: Board[]
}

export function Mobile({ text, lang, boards }: Props) {
    return (
        <div className={`${container.main.mobile} ${boards.length <= 0 && 'min-h-full'} mt-[16px]`}>
            {boards.length <= 0 && <div className="w-full my-[60px] text-center text-[24px] font-[500]">{text.noBoards}</div>}
            <div
                className={`
                    sm:grid-cols-2
                    grid grid-cols-1 gap-4
                `}
            >
                {boards.map((board) => (
                    <Link
                        key={board.id}
                        href={`/${lang}/boards/${board.id}`}
                        className="relative w-full shadow-sm rounded-md cursor-pointer bg-white"
                    >
                        <div
                            className={`
                                    hover:bg-black
                                    active:opacity-[0.16]
                                    absolute w-full h-full opacity-[0.08] z-10 rounded-md
                                `}
                        />
                        <div className="relative h-[317px] mb-2">
                            <img
                                className="w-full h-full rounded-md rounded-b-none object-contain"
                                src={board.image || '/images/text-logo-fit.png'}
                            />
                        </div>
                        <div className="flex gap-4 px-4 pb-4">
                            <img
                                className="w-[40px] h-[40px] border rounded-full"
                                src={board.profileUrl || '/images/logo1-removebg-preview.png'}
                                referrerPolicy="no-referrer"
                            />
                            <div className="flex flex-col justify-center">
                                <div className="w-[170px] whitespace-nowrap overflow-hidden text-ellipsis text-[14px]">{board.title}</div>
                                <div className="text-[12px]">{board.activityLocation}</div>
                            </div>
                        </div>
                    </Link>
                ))}
            </div>
        </div>
    )
}
