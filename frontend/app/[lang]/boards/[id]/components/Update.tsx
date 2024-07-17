'use client'

import React, { useEffect, useState } from 'react'
import { Locales } from '@/app/types/locales'
import { useRouter } from 'next/navigation'
import { useBoards } from '@/app/hooks'
import { IoMdSettings, IoMdArrowRoundBack } from 'react-icons/io'
import { MdModeEdit, MdDelete } from 'react-icons/md'

interface Props {
    lang: Locales
    boardId: string
    userId: number
}

interface List {
    name: string
    icon: any
    onClick: () => void
}

export default function Update({ lang, boardId, userId }: Props) {
    const [show, setShow] = useState(false)
    const [active, setActive] = useState(false)
    const { handleDelete, isMyBoard } = useBoards({ lang })

    const router = useRouter()
    const list: List[] = [
        { name: 'go back', icon: IoMdArrowRoundBack, onClick: () => router.push(`/${lang}/boards`) },
        { name: 'update', icon: MdModeEdit, onClick: () => router.push(`/${lang}/boards/update/${boardId}`) },
        { name: 'delete', icon: MdDelete, onClick: () => handleDelete(boardId) }
    ]

    useEffect(() => {
        isMyBoard(userId).then((result) => setShow(result))
    }, [])

    if (!show) return

    return (
        <div
            style={{
                width: active ? '280px' : '70px'
            }}
            className="fixed bottom-[80px] right-[80px] translate-x-1/2 translate-y-1/2 flex justify-center items-center transition-all duration-300 z-[30]"
        >
            <div
                onClick={() => setActive(!active)}
                className={`
                    hover:first:text-green-700
                    active:first:text-green-800
                    w-[70px] h-[70px] flex justify-center items-center bg-white rounded-full cursor-pointer shadow-md z-10
                `}
            >
                <IoMdSettings className="w-[25px] h-[25px]" />
            </div>
            {list.map((item, index) => {
                return (
                    <li
                        style={{
                            position: 'absolute',
                            left: active ? '0%' : '50%',
                            width: '60px',
                            height: '60px',
                            display: 'flex',
                            justifyContent: 'center',
                            alignItems: 'center',
                            borderRadius: '50%',
                            backgroundColor: 'white',
                            listStyle: 'none',
                            transition: 'all 0.3s',
                            transform: active ? `rotate(calc(360deg / 8 * ${index}))` : 'translateX(-50%)',
                            transformOrigin: active ? '140px' : '0',
                            cursor: 'pointer'
                        }}
                        className={`
                        hover:text-green-700
                        active:text-green-800 shadow-md
                        `}
                        onClick={item.onClick}
                        key={item.name}
                    >
                        <item.icon
                            style={{
                                width: '25px',
                                height: '25px',
                                transform: `rotate(calc(360deg / -8 * ${index}))`
                            }}
                        />
                    </li>
                )
            })}
        </div>
    )
}
