import React from 'react'
import { Button } from '@/app/common'
import { LocaleHeader, Locales } from '@/app/types/locales'
import { HeaderStates } from '@/app/hooks/useHeader'
import { useRouter } from 'next/navigation'
import { AiOutlineSearch } from 'react-icons/ai'

interface Props {
    lang: Locales
    text: LocaleHeader
    headerStates: HeaderStates
}

export default function Desktop({ lang, text, headerStates }: Props) {
    const router = useRouter()
    const { loading, search, accessToken, changeSearch, handleSearch } = headerStates

    return (
        <div
            className={`
                xl:flex 
                max-w-[1230px] h-[92px] justify-between items-center pb-4 pt-4 mx-auto hidden
            `}
        >
            <img
                className="w-[120px] h-[60px] cursor-pointer object-contain"
                src="/images/text-logo-fit.png"
                onClick={() => router.push(`/${lang}`)}
            />
            <div className="flex gap-8 items-center">
                <Button contents={text.registerWalker} href={`/${lang}/register-walker`} />
                <Button contents={text.boards} href={`/${lang}/boards`} />

                {!loading && accessToken && <Button contents={text.mypage} href={`/${lang}/mypage`} />}
                <form onSubmit={(e) => e.preventDefault()} className="relative">
                    <input
                        type="text"
                        value={search}
                        onChange={changeSearch}
                        className="w-[400px] pl-8 pr-[60px] py-4 text-[16px] text-black border bg-white rounded-[100px] outline-none"
                        placeholder={text.findMyLocal}
                    />
                    <button
                        type="submit"
                        onClick={() => handleSearch(lang)}
                        className="absolute top-[50%] right-4 translate-y-[-50%] w-[40px] h-[40px] flex justify-center items-center cursor-pointer"
                    >
                        <AiOutlineSearch className="text-[#898989]" />
                    </button>
                </form>
                {!loading && !accessToken && <Button contents={text.login} href={`/${lang}/signin`} />}
                {!loading && accessToken && <Button contents={text.logout} href={`/${lang}/signin`} />}
            </div>
        </div>
    )
}
