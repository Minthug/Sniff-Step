import React from 'react'
import Link from 'next/link'
import { D2CodingBold } from '@/app/fonts'
import { FaLongArrowAltLeft } from 'react-icons/fa'
import { GoogleLogin, SeparateLine, SigninButton, SigninInput, container } from '@/app/common'
import { useRouter } from 'next/navigation'
import { LoginStates } from '@/app/hooks'

interface Props {
    lang: string
    loginStates: LoginStates
}

export function Desktop({ lang, loginStates }: Props) {
    const router = useRouter()
    const { email, password, changeEmail, changePassword } = loginStates

    return (
        <div className={container.autentication.desktop.section}>
            <div className={container.autentication.desktop.sidebar}>
                <Link className="absolute left-8 h-[60px] flex gap-2 justify-center items-center animate-leftBounce" href={`/${lang}/`}>
                    <FaLongArrowAltLeft className="w-[20px] h-[20px]" />
                    <div className={`${D2CodingBold.className}`}>Home</div>
                </Link>
                <img
                    className="w-full h-full object-contain select-none bg-[#C9E2EB]"
                    src="https://cdn.dribbble.com/users/338126/screenshots/15483287/media/2f03c8290d612078b76883e579d4fd99.gif"
                />
                <img className="absolute bottom-4 w-[140px] object-cover" src="/text-logo.png" alt="" />
            </div>
            <div className={container.autentication.desktop.main}>
                <div className={`${D2CodingBold.className} text-[28px] font-bold mb-8`}>Sign in to Sniff & Step</div>
                <GoogleLogin>Sign in with Google</GoogleLogin>
                <SeparateLine>or</SeparateLine>
                <div className="my-4">
                    <div className={`${D2CodingBold.className} mb-1`}>Username or Email</div>
                    <SigninInput value={email} placeholder="Enter your username or email" type="text" onChange={changeEmail} />
                </div>
                <div className="mb-8">
                    <div className="flex justify-between">
                        <div className={`${D2CodingBold.className} mb-1`}>Password</div>
                        <button
                            onClick={() => router.push(`/${lang}/find-password`)}
                            className={`${D2CodingBold.className} text-[12px] underline select-none`}
                        >
                            Find Password
                        </button>
                    </div>
                    <SigninInput value={password} placeholder="Enter your password" type="password" onChange={changePassword} />
                </div>
                <SigninButton theme="dark" onClick={() => {}}>
                    Sign in
                </SigninButton>
                <div className="flex gap-2 text-[12px] justify-center">
                    <div>Don't have account?</div>
                    <button className="underline select-none" onClick={() => router.push(`/${lang}/signup`)}>
                        Sign Up
                    </button>
                </div>
            </div>
        </div>
    )
}
