import React from 'react'
import { container } from '../../styles'
import { AiOutlineSearch } from 'react-icons/ai'
import { LocaleHeader, Locales } from '@/app/types/locales'
import { useRouter } from 'next/navigation'

interface Props {
    lang: Locales
    text: LocaleHeader
    search: string
    changeSearch: (e: React.ChangeEvent<HTMLInputElement>) => void
    changeOnMobileSearch: () => void
    handleSearch: () => Promise<void>
}

export function MobileSearch({ lang, text, search, changeSearch, changeOnMobileSearch, handleSearch }: Props) {
    const router = useRouter()
    return (
        <div
            className={`
                        ${container.header.mobile}
                        absolute flex gap-4 justify-between items-center left-0 w-full h-full py-2 bg-neutral-50 z-10
                `}
        >
            <form
                onSubmit={(e) => {
                    e.preventDefault()
                }}
                className="relative w-full"
            >
                <input
                    type="text"
                    value={search}
                    onChange={changeSearch}
                    className="w-full pl-4 pr-[70px] py-3 text-[14px] border bg-white rounded-[100px] outline-none"
                    placeholder={text.findMyLocal}
                />
                <button
                    type="submit"
                    onClick={() => router.push(`/${lang}/boards/search?keyword=${search}&reload=true`)}
                    className="absolute top-[50%] right-4 translate-y-[-50%] w-[40px] h-[40px] flex justify-center items-center cursor-pointer"
                >
                    <AiOutlineSearch className="text-[#898989]" />
                </button>
            </form>
            <button
                className={`
                            hover:bg-slate-100 active:bg-slate-200
                            w-[50px] h-[40px] font-bold rounded-s
                    `}
                onClick={changeOnMobileSearch}
            >
                {text.cancel}
            </button>
        </div>
    )
}
