import React from 'react'
import { useRouter } from 'next/navigation'
import { LocaleHeader, Locales } from '@/app/types/locales'
import { container, Button } from '..'
import { AiOutlineSearch } from 'react-icons/ai'
import { HeaderStates } from '@/app/hooks/useHeader'

interface Props {
    lang: Locales
    text: LocaleHeader
    headerStates: HeaderStates
}

export default function Desktop({ lang, text, headerStates }: Props) {
    const router = useRouter()
    const { search, changeSearch } = headerStates

    return (
        <div className={container.header.desktop}>
            <img
                className="w-[220px] h-[60px] cursor-pointer object-contain translate-x-[-20px]"
                src="/images/text-logo-1.png"
                onClick={() => router.push(`/${lang}`)}
            />
            <div className="flex gap-10 items-center">
                <Button contents={text.registerWalker} href={`/${lang}/register-walker`} />
                <Button contents={text.boards} href={`/${lang}/boards`} />
                <form
                    onSubmit={(e) => {
                        e.preventDefault()
                    }}
                    className="relative"
                >
                    <input
                        type="text"
                        value={search}
                        onChange={changeSearch}
                        className="w-[400px] pl-8 pr-[60px] py-4 text-[16px] text-black border bg-white rounded-[100px] outline-none"
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
                <Button contents={text.login} href={`/${lang}/signin`} />
            </div>
        </div>
    )
}
