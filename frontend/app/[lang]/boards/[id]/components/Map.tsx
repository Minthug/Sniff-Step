'use client'

import React, { useEffect, useState } from 'react'
import { useKakaoLoader } from 'react-kakao-maps-sdk'
import { Map as KakaoMap } from 'react-kakao-maps-sdk'

interface Props {
    address: string
}

export function Map({ address }: Props) {
    const [map, setMap] = useState<kakao.maps.Map>()

    useKakaoLoader({
        appkey: process.env.NEXT_PUBLIC_KAKAO_CLIENT_ID || '',
        libraries: ['clusterer', 'drawing', 'services']
    })

    useEffect(() => {
        if (!map) return
        const ps = new kakao.maps.services.Places()
        ps.keywordSearch(address, (data, status, _pagination) => {
            if (status === kakao.maps.services.Status.OK) {
                const bounds = new kakao.maps.LatLngBounds()
                let markers = []

                for (let i = 0; i < data.length; i++) {
                    markers.push({
                        position: {
                            lat: data[i].y,
                            lng: data[i].x
                        },
                        content: data[i].place_name
                    })
                    bounds.extend(new kakao.maps.LatLng(Number(data[i].y), Number(data[i].x)))
                }
                map.setBounds(bounds)
                map.setMinLevel(4)
            }
        })
    }, [map])

    return (
        <KakaoMap
            id="map"
            center={{
                lat: 33.450701,
                lng: 126.570667
            }}
            className="w-full h-[350px] mb-8"
            level={4}
            onCreate={setMap}
            zoomable={false}
        />
    )
}
