import React from 'react'
import { container } from '@/app/common'
import { LocaleBoard, Locales } from '@/app/types/locales'
import { changeDayToKorean, changeTimeToKorean } from '@/app/utils/changeDateUtils'
import { Board } from '@/app/types/board'
import Link from 'next/link'

interface Props {
    lang: Locales
    text: LocaleBoard
    board: Board
    dates: string[]
}

export function Desktop({ lang, text, board, dates }: Props) {
    const { title, nickname, description, likeNumber, activityDate, activityTime, createdAt, image, profileUrl, address } = board
    return (
        <div className={container.main.desktop}>
            <div>
                <div className="w-full h-[500px] flex justify-center items-center mb-4 rounded-lg">
                    <img
                        className="w-[500px] h-full object-contain rounded-lg"
                        src={image || '/images/text-logo-1.png'}
                        alt={image || '/images/text-logo-1.png'}
                    />
                </div>
                <div className="w-full flex justify-between mb-8 pb-4 border-b select-none">
                    <div className="flex items-center gap-4">
                        <img className="w-[48px] h-[48px] border rounded-full" src={profileUrl || '/images/logo1-removebg-preview.png'} />
                        <div>
                            <div className="text-[18px] font-[600]">{nickname}</div>
                            <div className="text-[14px]">{address}</div>
                        </div>
                    </div>
                </div>
                <div className="flex items-center justify-between mb-8">
                    <div className="text-[32px] font-[500]">{title}</div>
                    <div className="text-[14px] text-gray-400">{new Date(createdAt).toLocaleString()}</div>
                </div>
                <div className="flex items-center gap-4 mb-12">
                    <div className="text-[24px] font-[500]">1. {text.address}</div>
                    <div className="text-[24px] font-[500] mt-[4px] border-b-2 border-red-600 leading-7">{address}</div>
                </div>
                <div className="flex flex-col gap-4 mb-12">
                    <div className="flex gap-4 items-center ">
                        <div className="text-[24px] font-[500]">2. {text.availableTime}</div>
                        {activityTime.map((time) => {
                            return (
                                <div key={time} className="text-[24px] font-[500] mt-[4px] border-b-2 border-red-600 leading-7">
                                    {changeTimeToKorean(lang, time)}
                                </div>
                            )
                        })}
                    </div>
                    <div className="flex gap-4">
                        {dates.map((date) => {
                            return (
                                <div className="flex items-center" key={date}>
                                    <input type="checkbox" defaultChecked={activityDate.includes(date.toUpperCase())} disabled />
                                    <div>{changeDayToKorean(lang, date)}</div>
                                </div>
                            )
                        })}
                    </div>
                </div>
                <div className="mb-12">
                    <div className="flex items-center justify-between text-[18px] font-[500] mb-4">
                        <div className="text-[24px] font-[500]">3. {text.description}</div>
                    </div>
                    <div className="w-full h-full min-h-[300px] p-4 border rounded-md resize-none outline-none whitespace-pre-wrap bg-white">
                        {description}
                    </div>
                </div>
            </div>
            <Link
                className={`
                            hover:bg-gray-100
                            active:bg-gray-200
                            w-full flex gap-4 items-center justify-center py-4 border rounded-md cursor-pointer bg-white
                        `}
                href={`/${lang}/boards`}
            >
                <div className="flex gap-2 items-center cursor-pointer font-[700]">{text.boardsButton}</div>
            </Link>
        </div>
    )
}
