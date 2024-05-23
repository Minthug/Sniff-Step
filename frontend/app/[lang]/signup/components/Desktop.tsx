import React from 'react'
import { GoogleButton, SeparateLine, LargeButton, container } from '@/app/common'
import { LocaleSignup } from '@/app/types/locales'
import { LoginStates } from '@/app/hooks'
import { D2CodingBold } from '@/app/fonts'
import { FaLongArrowAltLeft } from 'react-icons/fa'
import { useRouter } from 'next/navigation'
import Link from 'next/link'

interface Props {
    lang: string
    text: LocaleSignup
    loginStates: LoginStates
}

export function Desktop({ lang, text, loginStates }: Props) {
    const router = useRouter()
    const { handleGoogleLogin } = loginStates

    return (
        <div className={container.autentication.desktop.section}>
            <div className={container.autentication.desktop.sidebar}>
                <Link
                    className="absolute left-8 h-[60px] flex gap-2 justify-center items-center animate-leftBounce z-10"
                    href={`/${lang}/signin`}
                >
                    <FaLongArrowAltLeft className="w-[20px] h-[20px]" />
                    <div className={`${D2CodingBold.className} text-[14px]`}>{text.signin}</div>
                </Link>
                <video
                    className="w-full h-full object-contain select-none bg-[#a6bee4]"
                    autoPlay
                    muted
                    loop
                    src="https://cdn.dribbble.com/users/1341046/screenshots/16057590/media/db8ececb62cdd03ddaa78fab991a4ec3.mp4"
                />
                <img className="absolute bottom-4 w-[140px] object-cover" src="/images/text-logo-padding.png" alt="" />
            </div>
            <div className={container.autentication.desktop.main}>
                <div className={`${D2CodingBold.className} text-[28px] font-bold mb-8`}>{text.title}</div>
                <GoogleButton theme="dark" onClick={handleGoogleLogin}>
                    {text.signupGoogle}
                </GoogleButton>
                <SeparateLine className="mb-4">or</SeparateLine>
                <LargeButton onClick={() => router.push(`/${lang}/signup/email-password`)}>{text.signupEmail}</LargeButton>
                <div className="flex flex-wrap justify-center mb-4 text-[12px] mt-8 px-[60px]">
                    {text.introduceTermsOfService}
                    &nbsp;
                    <button className="underline select-none">{text.termsOfService}</button>
                    ,&nbsp;
                    <button className="underline select-none">{text.privacyPolicy}</button>
                </div>
                <div className="flex justify-center text-[14px]">
                    {text.introduceAlreadyHaveAccount}&nbsp;
                    <Link className="underline select-none" href={`/${lang}/signin`}>
                        {text.signin}
                    </Link>
                </div>
            </div>
        </div>
    )
}
