import React from 'react'
import Link from 'next/link'
import { D2CodingBold } from '@/app/fonts'
import { LocaleSignin } from '@/app/types/locales'
import { useRouter } from 'next/navigation'
import { LoginStates } from '@/app/hooks'
import { GoogleButton, SeparateLine, LargeButton, Input, TextLogo, container } from '@/app/common'
import { FaLongArrowAltLeft } from 'react-icons/fa'

interface Props {
    lang: string
    text: LocaleSignin
    loginStates: LoginStates
}

export function Desktop({ lang, text, loginStates }: Props) {
    const router = useRouter()
    const {
        email,
        password,
        emailError,
        passwordError,
        passwordLengthError,
        passwordLetterError,
        changeEmail,
        changePassword,
        handleGetProfile,
        handleLogin,
        handleGoogleLogin
    } = loginStates

    return (
        <div className={container.autentication.desktop.section}>
            <div className={container.autentication.desktop.sidebar}>
                <Link className="absolute left-8 h-[60px] flex gap-2 justify-center items-center animate-leftBounce" href={`/${lang}/`}>
                    <FaLongArrowAltLeft className="w-[20px] h-[20px]" />
                    <div className={`${D2CodingBold.className}`}>{text.goHome}</div>
                </Link>
                <img
                    className="w-full h-full object-contain select-none bg-[#C9E2EB]"
                    src="https://cdn.dribbble.com/users/338126/screenshots/15483287/media/2f03c8290d612078b76883e579d4fd99.gif"
                />
                <img className="absolute bottom-4 w-[140px] object-cover" src="/images/text-logo.png" alt="" />
            </div>
            <div className={container.autentication.desktop.main}>
                <TextLogo lang={lang} />
                <GoogleButton onClick={handleGoogleLogin}>{text.signinGoogle}</GoogleButton>
                <SeparateLine>or</SeparateLine>
                <div className="my-4">
                    <div className={`font-[600] mb-1 tracking-wide`}>{text.email}</div>
                    <Input type="text" value={email} onChange={changeEmail} placeholder={text.emailPlaceholder} />
                </div>
                {emailError && <div className="text-red-500 text-[12px] mb-4">{text.emailError}</div>}
                <div className="mb-8">
                    <div className="flex justify-between">
                        <div className={`font-[600] mb-1 tracking-wide`}>{text.password}</div>
                    </div>
                    <Input type="password" value={password} onChange={changePassword} placeholder={text.passwordPlaceholder} />
                    {passwordError && <div className="text-red-500 text-[12px] my-4">{text.passwordError}</div>}
                    {passwordLengthError && <div className="text-red-500 text-[12px] mb-4">{text.passwordLengthError}</div>}
                    {passwordLetterError && <div className="text-red-500 text-[12px]">{text.passwordLetterError}</div>}
                </div>
                <LargeButton
                    theme="dark"
                    onClick={async () => {
                        try {
                            const accessToken = await handleLogin()
                            await handleGetProfile(accessToken)
                            router.push(`/${lang}`)
                        } catch (err) {
                            console.log(err)
                        }
                    }}
                >
                    {text.signin}
                </LargeButton>
                <div className="flex gap-2 text-[12px] justify-center">
                    <div>{text.signupIntroduce}</div>
                    <button className="underline select-none" onClick={() => router.push(`/${lang}/signup`)}>
                        {text.signup}
                    </button>
                </div>
            </div>
        </div>
    )
}
