import React from 'react'
import Link from 'next/link'
import { LocaleSignup, Locales } from '@/app/types/locales'
import { SignupStates } from '@/app/hooks/useSignup'
import { D2CodingBold } from '@/app/fonts'
import { FaLongArrowAltLeft } from 'react-icons/fa'
import { LargeButton, Input, container } from '@/app/common'
import { useRouter } from 'next/navigation'

interface Props {
    lang: Locales
    text: LocaleSignup
    signupStates: SignupStates
}

export function Desktop({ lang, text, signupStates }: Props) {
    const router = useRouter()
    const {
        nickname,
        email,
        password,
        phoneNumber,
        nicknameError,
        emailError,
        passwordError,
        passwordLengthError,
        passwordLetterError,
        phoneNumberError,
        isAgreedError,
        changeNickname,
        changeEmail,
        changePassword,
        changePhoneNumber,
        changeIsAgreed,
        handleLogin,
        handleRegister,
        handleGetProfile
    } = signupStates

    return (
        <div className={container.autentication.desktop.section}>
            <div className={container.autentication.desktop.sidebar}>
                <Link
                    className="absolute left-8 h-[60px] flex gap-2 justify-center items-center animate-leftBounce z-10"
                    href={`/${lang}/signup`}
                >
                    <FaLongArrowAltLeft className="w-[20px] h-[20px]" />
                    <div className={`${D2CodingBold.className} text-[14px]`}>{text.beforeSignup}</div>
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
            <div className={`${container.autentication.desktop.main} py-8`}>
                <div className={`${D2CodingBold.className} text-[28px] mb-4`}>{text.title}</div>
                <div className="flex gap-4 mb-4">
                    <div className="w-full">
                        <div className={`${D2CodingBold.className} mb-2`}>{text.nickname}</div>
                        <Input type="text" value={nickname} onChange={changeNickname} placeholder={text.nicknamePlaceholder} />
                    </div>
                </div>
                {nicknameError && <div className="text-red-500 text-[12px] mb-4">{text.nicknameError}</div>}
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>{text.email}</div>
                    <Input type="text" value={email} onChange={changeEmail} placeholder={text.emailPlaceholder} />
                </div>
                {emailError && <div className="text-red-500 text-[12px] mb-4">{text.emailError}</div>}
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>{text.password}</div>
                    <Input type="password" value={password} onChange={changePassword} placeholder={text.passwordPlaceholder} />
                </div>
                {passwordError && <div className="text-red-500 text-[12px] mb-4">{text.passwordError}</div>}
                {passwordLengthError && <div className="text-red-500 text-[12px] mb-4">{text.passwordLengthError}</div>}
                {passwordLetterError && <div className="text-red-500 text-[12px] mb-4">{text.passwordLetterError}</div>}
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>{text.phoneNumber}</div>
                    <Input type="text" value={phoneNumber} onChange={changePhoneNumber} placeholder={text.phoneNumberPlaceholder} />
                </div>
                {phoneNumberError && <div className="text-red-500 text-[12px] mb-4">{text.phoneNumberError}</div>}
                <div className="flex items-center gap-4 mb-8">
                    <input type="checkbox" className="w-[20px] h-[20px]" onChange={changeIsAgreed} />
                    <div className="flex flex-wrap text-[12px]">
                        <div>{text.agreeTerms}&nbsp;</div>
                        <button className="underline select-none">{text.termsOfService}</button>,&nbsp;
                        <button className="underline select-none">{text.privacyPolicy}</button>
                    </div>
                </div>
                {isAgreedError && <div className="text-red-500 text-[12px] mb-4">{text.isAgreedError}</div>}
                <LargeButton
                    theme="dark"
                    onClick={async () => {
                        const isRegister = await handleRegister()
                        if (isRegister) {
                            const accessToken = await handleLogin()
                            handleGetProfile(accessToken)
                            router.push(`/${lang}/`)
                        }
                    }}
                >
                    {text.signup}
                </LargeButton>
                <div className="flex justify-center items-center text-[12px]">
                    {text.introduceAlreadyHaveAccount}&nbsp;
                    <Link className="underline select-none" href={`/${lang}/signin`}>
                        {text.signin}
                    </Link>
                </div>
            </div>
        </div>
    )
}
