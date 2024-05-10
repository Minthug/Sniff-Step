'use client'

import React, { useEffect, useState } from 'react'
import { Locales } from '@/app/types/locales'
import { useFetch } from '@/app/hooks/useFetch'
import { Profile } from '@/app/types/profile'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    const [profile, setProfile] = useState<Profile>()

    const { customFetch } = useFetch()
    useEffect(() => {
        const accessToken = localStorage.getItem('accessToken')
        customFetch('/api/auth/profile', {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        })
            .then(async (res) => {
                if (res) {
                    const { data } = await res.json()
                    setProfile(data)
                }
            })
            .catch((err) => console.log(err))
    }, [])

    return (
        <div className={`xl:flex h-full max-w-[1230px] mx-auto min-h-fit flex-col hidden`}>
            <div className="h-[250px] flex flex-col justify-center items-center gap-4 my-8 select-none">
                <div className="w-[180px] h-[180px]">
                    <img className="w-full h-full object-contain border rounded-full" src={profile?.profileImage} alt="" />
                </div>
                <div className="h-[57px]">
                    <div className="text-[24px] text-center">{profile?.nickname}</div>
                    <div className="text-[14px] text-gray-600 text-center">{profile?.email}</div>
                </div>
            </div>
        </div>
    )
}
