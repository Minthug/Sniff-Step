import React, { HTMLAttributes } from 'react'
import { LocaleHeader, Locales } from '@/app/types/locales'
import { HeaderStates } from '@/app/hooks/useHeader'
import { useRouter } from 'next/navigation'
import { D2CodingBold } from '@/app/fonts'
import { AiOutlineMenu, AiOutlineSearch } from 'react-icons/ai'

interface Props {
    lang: Locales
    text: LocaleHeader
    headerStates: HeaderStates
}

function MobileCategory({ children, onClick }: HTMLAttributes<HTMLButtonElement>) {
    return (
        <button
            className={`
                hover:bg-slate-100 active:bg-slate-200
                w-full flex justify-center py-2 cursor-pointer
            `}
            onClick={onClick}
        >
            {children}
        </button>
    )
}

export default function Mobile({ lang, text, headerStates }: Props) {
    const router = useRouter()
    const { search, onMobileMenu, onMobileSearch, accessToken, changeSearch, changeOnMobileMenu, changeOnMobileSearch, handleSearch } =
        headerStates

    return (
        <div
            className={`
                xl:hidden 
                fixed top-0 w-full min-h-[76px] flex items-center px-2 py-2 bg-[#fcfcfc] z-[100]
            `}
        >
            {!onMobileSearch && (
                <div className="w-full flex justify-between items-center">
                    <img
                        className="w-[160px] h-[30px] cursor-pointer object-contain"
                        src="/images/text-logo-fit.png"
                        onClick={() => router.push(`/${lang}`)}
                    />
                    <div className="flex gap-4">
                        <div
                            className="w-[40px] h-[40px] flex justify-center items-center cursor-pointer select-none"
                            onClick={changeOnMobileSearch}
                        >
                            <AiOutlineSearch className="text-[24px]" />
                        </div>
                        <div
                            className="w-[40px] h-[40px] flex justify-center items-center cursor-pointer select-none"
                            onClick={changeOnMobileMenu}
                        >
                            <AiOutlineMenu className="text-[24px]" />
                        </div>
                    </div>
                </div>
            )}

            {onMobileSearch && (
                <div className="w-full h-full flex justify-between items-center gap-4 bg-[#fcfcfc] z-[50]">
                    <form onSubmit={(e) => e.preventDefault()} className="relative w-full">
                        <input
                            type="text"
                            value={search}
                            onChange={changeSearch}
                            className="w-full pl-4 pr-[70px] py-3 text-[14px] border bg-white rounded-[10px] outline-none"
                            placeholder={text.findMyLocal}
                        />
                        <button
                            type="submit"
                            onClick={() => handleSearch(lang)}
                            className="absolute top-[50%] right-4 translate-y-[-50%] w-[40px] h-[40px] flex justify-center items-center cursor-pointer"
                        >
                            <AiOutlineSearch className="w-[20px] h-[20px] text-[#898989]" />
                        </button>
                    </form>
                    <button
                        className={`
                            hover:bg-slate-100 active:bg-slate-200
                            min-w-fit h-[40px] font-bold rounded-s px-2
                        `}
                        onClick={changeOnMobileSearch}
                    >
                        {text.cancel}
                    </button>
                </div>
            )}

            <div
                style={{
                    height: onMobileMenu ? '141px' : '0px',
                    opacity: onMobileMenu ? 1 : 0,
                    transition: onMobileMenu ? 'height 0.3s, opacity 0.3s' : 'height 0.5s',
                    padding: onMobileMenu ? '0 0 16px 0' : '0'
                }}
                className={`
                    ${D2CodingBold.className}
                    absolute left-0 top-[75px] w-full items-center bg-[#fcfcfc] text-[18px] z-[20]
                `}
            >
                {onMobileMenu && (
                    <>
                        <MobileCategory onClick={() => router.push(`/${lang}/boards/post`)}>{text.post}</MobileCategory>
                        <MobileCategory onClick={() => router.push(`/${lang}/boards`)}>{text.boards}</MobileCategory>
                        <MobileCategory onClick={() => router.push(`/${lang}/signin`)}>{text.login}</MobileCategory>
                    </>
                )}
            </div>

            {accessToken && (
                <div
                    style={{
                        height: onMobileMenu ? '184px' : '0px',
                        opacity: onMobileMenu ? 1 : 0,
                        transition: onMobileMenu ? 'height 0.3s, opacity 0.3s' : 'height 0.5s',
                        padding: onMobileMenu ? '0 0 16px 0' : '0'
                    }}
                    className={`
                                ${D2CodingBold.className}
                                absolute left-0 top-[75px] w-full items-center bg-[#fcfcfc] text-[18px] z-[20]
                            `}
                >
                    {onMobileMenu && (
                        <>
                            <MobileCategory onClick={() => router.push(`/${lang}/boards/post`)}>{text.post}</MobileCategory>
                            <MobileCategory onClick={() => router.push(`/${lang}/boards`)}>{text.boards}</MobileCategory>
                            <MobileCategory onClick={() => router.push(`/${lang}/mypage`)}>{text.mypage}</MobileCategory>
                            <MobileCategory onClick={() => router.push(`/${lang}/signin`)}>{text.logout}</MobileCategory>
                        </>
                    )}
                </div>
            )}

            {onMobileMenu && (
                <div
                    onClick={changeOnMobileMenu}
                    style={{
                        height: '100%',
                        minHeight: 'calc(100vh - 56px)'
                    }}
                    className={`absolute left-0 top-[75px] h-[100%] w-full bg-[#222] opacity-[60%] z-10`}
                />
            )}
        </div>
    )
}
