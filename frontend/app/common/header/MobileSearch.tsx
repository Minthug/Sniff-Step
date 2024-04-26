import React from 'react'
import { container } from '../styles'
import { AiOutlineSearch } from 'react-icons/ai'
import { LocaleHeader } from '@/app/types/locales'

interface Props {
    text: LocaleHeader
    setOnMobileSearch: (onMobileSearch: boolean) => void
}

export function MobileSearch({ text, setOnMobileSearch }: Props) {
    return (
        <div
            className={`
                        ${container.header}
                        absolute flex gap-4 justify-between items-center left-0 w-full h-full py-2 bg-neutral-50 z-10
                `}
        >
            <div className="relative w-full">
                <input
                    className="w-full pl-4 pr-[70px] py-3 text-[14px] text-[#898989] border bg-white rounded-[100px] outline-none"
                    onChange={() => {}}
                    type="text"
                    placeholder={text.findMyLocal}
                />
                <div className="absolute top-[50%] right-4 translate-y-[-50%] w-[40px] h-[40px] flex justify-center items-center cursor-pointer">
                    <AiOutlineSearch className="text-[#898989]" />
                </div>
            </div>
            <button
                className={`
                            hover:bg-slate-100 active:bg-slate-200
                            w-[50px] h-[40px] font-bold rounded-s
                    `}
                onClick={() => setOnMobileSearch(false)}
            >
                {text.cancel}
            </button>
        </div>
    )
}
